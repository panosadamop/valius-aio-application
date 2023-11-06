import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('FieldPreferredCommunicationChannel e2e test', () => {
  const fieldPreferredCommunicationChannelPageUrl = '/field-preferred-communication-channel';
  const fieldPreferredCommunicationChannelPageUrlPattern = new RegExp('/field-preferred-communication-channel(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const fieldPreferredCommunicationChannelSample = {};

  let fieldPreferredCommunicationChannel;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/field-preferred-communication-channels+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/field-preferred-communication-channels').as('postEntityRequest');
    cy.intercept('DELETE', '/api/field-preferred-communication-channels/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (fieldPreferredCommunicationChannel) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/field-preferred-communication-channels/${fieldPreferredCommunicationChannel.id}`,
      }).then(() => {
        fieldPreferredCommunicationChannel = undefined;
      });
    }
  });

  it('FieldPreferredCommunicationChannels menu should load FieldPreferredCommunicationChannels page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('field-preferred-communication-channel');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('FieldPreferredCommunicationChannel').should('exist');
    cy.url().should('match', fieldPreferredCommunicationChannelPageUrlPattern);
  });

  describe('FieldPreferredCommunicationChannel page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(fieldPreferredCommunicationChannelPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create FieldPreferredCommunicationChannel page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/field-preferred-communication-channel/new$'));
        cy.getEntityCreateUpdateHeading('FieldPreferredCommunicationChannel');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldPreferredCommunicationChannelPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/field-preferred-communication-channels',
          body: fieldPreferredCommunicationChannelSample,
        }).then(({ body }) => {
          fieldPreferredCommunicationChannel = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/field-preferred-communication-channels+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/field-preferred-communication-channels?page=0&size=20>; rel="last",<http://localhost/api/field-preferred-communication-channels?page=0&size=20>; rel="first"',
              },
              body: [fieldPreferredCommunicationChannel],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(fieldPreferredCommunicationChannelPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details FieldPreferredCommunicationChannel page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('fieldPreferredCommunicationChannel');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldPreferredCommunicationChannelPageUrlPattern);
      });

      it('edit button click should load edit FieldPreferredCommunicationChannel page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FieldPreferredCommunicationChannel');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldPreferredCommunicationChannelPageUrlPattern);
      });

      it('edit button click should load edit FieldPreferredCommunicationChannel page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('FieldPreferredCommunicationChannel');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldPreferredCommunicationChannelPageUrlPattern);
      });

      it('last delete button click should delete instance of FieldPreferredCommunicationChannel', () => {
        cy.intercept('GET', '/api/field-preferred-communication-channels/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('fieldPreferredCommunicationChannel').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', fieldPreferredCommunicationChannelPageUrlPattern);

        fieldPreferredCommunicationChannel = undefined;
      });
    });
  });

  describe('new FieldPreferredCommunicationChannel page', () => {
    beforeEach(() => {
      cy.visit(`${fieldPreferredCommunicationChannelPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('FieldPreferredCommunicationChannel');
    });

    it('should create an instance of FieldPreferredCommunicationChannel', () => {
      cy.get(`[data-cy="preferredCommunicationChannel"]`).type('auxiliary Gardens reboot').should('have.value', 'auxiliary Gardens reboot');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        fieldPreferredCommunicationChannel = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', fieldPreferredCommunicationChannelPageUrlPattern);
    });
  });
});

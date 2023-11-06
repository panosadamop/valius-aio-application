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

describe('SegmentUniqueCharacteristic e2e test', () => {
  const segmentUniqueCharacteristicPageUrl = '/segment-unique-characteristic';
  const segmentUniqueCharacteristicPageUrlPattern = new RegExp('/segment-unique-characteristic(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const segmentUniqueCharacteristicSample = { value: 'bandwidth-monitored Kids', category: 'quantify morph primary', language: 'GREEK' };

  let segmentUniqueCharacteristic;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/segment-unique-characteristics+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/segment-unique-characteristics').as('postEntityRequest');
    cy.intercept('DELETE', '/api/segment-unique-characteristics/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (segmentUniqueCharacteristic) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/segment-unique-characteristics/${segmentUniqueCharacteristic.id}`,
      }).then(() => {
        segmentUniqueCharacteristic = undefined;
      });
    }
  });

  it('SegmentUniqueCharacteristics menu should load SegmentUniqueCharacteristics page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('segment-unique-characteristic');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('SegmentUniqueCharacteristic').should('exist');
    cy.url().should('match', segmentUniqueCharacteristicPageUrlPattern);
  });

  describe('SegmentUniqueCharacteristic page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(segmentUniqueCharacteristicPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create SegmentUniqueCharacteristic page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/segment-unique-characteristic/new$'));
        cy.getEntityCreateUpdateHeading('SegmentUniqueCharacteristic');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', segmentUniqueCharacteristicPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/segment-unique-characteristics',
          body: segmentUniqueCharacteristicSample,
        }).then(({ body }) => {
          segmentUniqueCharacteristic = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/segment-unique-characteristics+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/segment-unique-characteristics?page=0&size=20>; rel="last",<http://localhost/api/segment-unique-characteristics?page=0&size=20>; rel="first"',
              },
              body: [segmentUniqueCharacteristic],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(segmentUniqueCharacteristicPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details SegmentUniqueCharacteristic page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('segmentUniqueCharacteristic');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', segmentUniqueCharacteristicPageUrlPattern);
      });

      it('edit button click should load edit SegmentUniqueCharacteristic page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SegmentUniqueCharacteristic');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', segmentUniqueCharacteristicPageUrlPattern);
      });

      it('edit button click should load edit SegmentUniqueCharacteristic page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('SegmentUniqueCharacteristic');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', segmentUniqueCharacteristicPageUrlPattern);
      });

      it('last delete button click should delete instance of SegmentUniqueCharacteristic', () => {
        cy.intercept('GET', '/api/segment-unique-characteristics/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('segmentUniqueCharacteristic').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', segmentUniqueCharacteristicPageUrlPattern);

        segmentUniqueCharacteristic = undefined;
      });
    });
  });

  describe('new SegmentUniqueCharacteristic page', () => {
    beforeEach(() => {
      cy.visit(`${segmentUniqueCharacteristicPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('SegmentUniqueCharacteristic');
    });

    it('should create an instance of SegmentUniqueCharacteristic', () => {
      cy.get(`[data-cy="value"]`).type('input').should('have.value', 'input');

      cy.get(`[data-cy="category"]`).type('Pizza success viral').should('have.value', 'Pizza success viral');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('ENGLISH');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        segmentUniqueCharacteristic = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', segmentUniqueCharacteristicPageUrlPattern);
    });
  });
});

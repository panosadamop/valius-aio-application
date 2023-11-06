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

describe('CompetitorCompetitivePosition e2e test', () => {
  const competitorCompetitivePositionPageUrl = '/competitor-competitive-position';
  const competitorCompetitivePositionPageUrlPattern = new RegExp('/competitor-competitive-position(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const competitorCompetitivePositionSample = { value: 'Steel Oregon deploy', language: 'GREEK' };

  let competitorCompetitivePosition;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/competitor-competitive-positions+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/competitor-competitive-positions').as('postEntityRequest');
    cy.intercept('DELETE', '/api/competitor-competitive-positions/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (competitorCompetitivePosition) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/competitor-competitive-positions/${competitorCompetitivePosition.id}`,
      }).then(() => {
        competitorCompetitivePosition = undefined;
      });
    }
  });

  it('CompetitorCompetitivePositions menu should load CompetitorCompetitivePositions page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('competitor-competitive-position');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('CompetitorCompetitivePosition').should('exist');
    cy.url().should('match', competitorCompetitivePositionPageUrlPattern);
  });

  describe('CompetitorCompetitivePosition page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(competitorCompetitivePositionPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create CompetitorCompetitivePosition page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/competitor-competitive-position/new$'));
        cy.getEntityCreateUpdateHeading('CompetitorCompetitivePosition');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', competitorCompetitivePositionPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/competitor-competitive-positions',
          body: competitorCompetitivePositionSample,
        }).then(({ body }) => {
          competitorCompetitivePosition = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/competitor-competitive-positions+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/competitor-competitive-positions?page=0&size=20>; rel="last",<http://localhost/api/competitor-competitive-positions?page=0&size=20>; rel="first"',
              },
              body: [competitorCompetitivePosition],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(competitorCompetitivePositionPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details CompetitorCompetitivePosition page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('competitorCompetitivePosition');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', competitorCompetitivePositionPageUrlPattern);
      });

      it('edit button click should load edit CompetitorCompetitivePosition page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CompetitorCompetitivePosition');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', competitorCompetitivePositionPageUrlPattern);
      });

      it('edit button click should load edit CompetitorCompetitivePosition page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('CompetitorCompetitivePosition');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', competitorCompetitivePositionPageUrlPattern);
      });

      it('last delete button click should delete instance of CompetitorCompetitivePosition', () => {
        cy.intercept('GET', '/api/competitor-competitive-positions/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('competitorCompetitivePosition').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', competitorCompetitivePositionPageUrlPattern);

        competitorCompetitivePosition = undefined;
      });
    });
  });

  describe('new CompetitorCompetitivePosition page', () => {
    beforeEach(() => {
      cy.visit(`${competitorCompetitivePositionPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('CompetitorCompetitivePosition');
    });

    it('should create an instance of CompetitorCompetitivePosition', () => {
      cy.get(`[data-cy="value"]`).type('online').should('have.value', 'online');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('ENGLISH');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        competitorCompetitivePosition = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', competitorCompetitivePositionPageUrlPattern);
    });
  });
});
